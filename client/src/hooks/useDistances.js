import { useState, useEffect } from 'react';
import { LOG } from '../utils/constants';
import { sendAPIRequest } from '../utils/restfulAPI';

export function useDistances(places, earthRadius, serverSettings) {
    const [leg, setLeg] = useState([]);
    const [cumulative, setCumulative] = useState([]);
    const [total, setTotal] = useState(0);

    const distances = {
      leg: leg,
      cumulative: cumulative,
      total: total
    }
    
    const distanceActions = {
      setLeg: setLeg,
      setCumulative: setCumulative,
      setTotal: setTotal
    }
    
    useEffect(() => {makeDistancesRequest(places, earthRadius, serverSettings, distanceActions);},
              [places,earthRadius])
  
    return {distances};
}

async function makeDistancesRequest(places, earthRadius, serverSettings, distanceActions) {  

  const {setLeg, setCumulative, setTotal} = distanceActions;

  const requestBody = { requestType: "distances", places: places, earthRadius: earthRadius };

  if(serverSettings != null && serverSettings.serverConfig != null && serverSettings.serverConfig.features.includes("distances")){
    const distancesResponse = await sendAPIRequest(requestBody, serverSettings.serverURL);
    setLeg(distancesResponse.distances);
    setCumulative(calcCumulative(distancesResponse.distances))
    setTotal(calcTotal(distancesResponse.distances)) 
  }

  else{ 
    let list_of_zero = places.map((place) => (0))
    setLeg(list_of_zero);
    setCumulative(list_of_zero)
    setTotal(0)  
  }
}

function calcCumulative(distances){
  return distances.map((accumulator,runningSum) => runningSum + accumulator, runningSum);
}

function calcTotal(distances){
  return distances.reduce((accumulator, currentValue)=>accumulator + currentValue, currentValue);
}