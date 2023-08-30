import { useState, useEffect } from 'react';
import { LOG } from '../utils/constants';
import { sendAPIRequest } from '../utils/restfulAPI';

export function useDistances(places, earthRadius, serverURL) {
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
    
    useEffect(() => {makeDistancesRequest(places, earthRadius, serverURL, distanceActions);},
              [places,earthRadius])
  
    return {distances};
}

async function makeDistancesRequest(places, earthRadius, serverURL, distanceActions) {
  
    const {setLeg, setCumulative, setTotal} = distanceActions;

    const requestBody = { requestType: "distances", places: places, earthRadius: earthRadius };

    // This statement and logic needs to be adjusted once you have implemented the server side of distances.
    // Happy coding!
    let distances_API_implemented = false
    if (distances_API_implemented) {
      const distancesResponse = await sendAPIRequest(requestBody, serverURL);
      setLeg(distancesResponse.distances);
      setCumulative(calcCumulative(distancesResponse.distances))
      setTotal(calcTotal(distancesResponse.distances))
    }

    else{
      setLeg([]);
      setCumulative([])
      setTotal([])    
    }
}

function calcCumulative(distances){
  let cumulativeArray = [];
  let runningSum = 0;
  for (let index = 0; index < distances.length; i++){
    runningSum += distances[index];
    cumulativeArray.push(runningSum);
  }
  return cumulativeArray;
}

function calcTotal(distances){
  return distances.reduce((accumulator, currentValue)=>accumulator + currentValue, 0);
}