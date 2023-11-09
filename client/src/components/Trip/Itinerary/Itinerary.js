import React, { useState, useEffect } from 'react';
import { useToggle } from '../../../hooks/useToggle';
import { Table, Collapse, Button } from 'reactstrap';
import { latLngToText, placeToLatLng } from '../../../utils/transformers';
import { BsChevronDown } from 'react-icons/bs';
import PlaceActions from './PlaceActions';
import Distance from './Distance';
import Units from './Units';
import {useDistances} from "../../../hooks/useDistances";
import {LuArrowBigRight, LuArrowBigRightDash } from 'react-icons/lu';
import {sendAPIRequest} from "../../../utils/restfulAPI";

export default function Itinerary(props) {

	const [earthRadius, setEarthRadius] = useState(3959.0);
	const [distanceUnits, setDistanceUnits] = useState("miles");
	const [response, setResponse] = useState (1);
	const serverSettings = props.serverSettings;
	//const serverUrl = "http://localhost:41326";

	console.log("serverSettings:", serverSettings);

	useEffect(() => {
    }, [props.serverSettings]);

	const {distances} = useDistances(props.places, earthRadius, props.serverSettings);

	const placeListProps = {
		places: props.places,
		distances: distances,
		placeActions: props.placeActions,
		selectedIndex: props.selectedIndex,
		serverSettings: serverSettings
	}

	const unitsProps = {
		earthRadius: earthRadius,
		setEarthRadius: setEarthRadius,
		distanceUnits: distanceUnits,
		setDistanceUnits: setDistanceUnits,
	}
	const tourProps = {
		earthRadius: earthRadius,
		setEarthRadius: setEarthRadius,
		response: response,
		setResponse: setResponse,
	}

	const total = distances.total;
	
	return (
		<Table responsive>
			<TripHeader
				{...placeListProps}
				{...unitsProps}
				{...tourProps}
				tripName={props.tripName}
				places = {props.places}
				serverSettings = {serverSettings}
				total = {total}
				//setPlaces={props.setPlaces}
			/>
			<PlaceList 
				{...placeListProps}
			/>
		</Table>
	);
}
function createRequestBody(props) {
	const requestBody = {
        "requestType": "tour",
        "earthRadius": props.earthRadius,
        "response": props.response,
		"places": props.places.map(place => ({
            latitude: place.latitude,
            longitude: place.longitude,
            name: place.defaultDisplayName || "Unknown", // Provide a default name if it's undefined
        })),
    };
	console.log("Request body:", requestBody);
    return requestBody
}

async function optimizeTour (props) {
	console.log("Entered Optimize TOUR");
	//console.log("serverUrl", serverSettings.serverUrl);
	console.log("Props in optimizeTour:", props);
	const requestBody = createRequestBody(props);
	console.log("Request body:", requestBody);

	try{
		if (!props.places) {
            console.error("Error: props.places is not defined");
            return;
        }

		
		const responseBody = await sendAPIRequest(requestBody, "http://localhost:41326");
		console.log("Response from API:", responseBody);

		if (responseBody && responseBody.places) {
			console.log("Response from API:", responseBody);
			const optimizedPlaces = responseBody.places.map(place => ({
				latitude: place.latitude,
				longitude: place.longitude,
				name: place.defaultDisplayName || "Unknown", // Provide a default name if it's undefined
			}))
			console.log("optimizedPlaces:" + optimizedPlaces);
			//setPlaces(optimizedPlaces);
		  } else {
			console.log("Response from API:", responseBody);
			console.log("Server response does not contain valid 'places' data.");
		  }
	}
	catch (e) {
		console.log("Error in API request:", e);
	}
}

function TripHeader(props) {
	return (
		
		<thead>
			<tr>
				<th
					className='trip-header-title'
					data-testid='trip-header-title'
				>
					{props.tripName} is <Distance distance={props.total}/> <Units {...props}/>
				</th>

				<td>
					<Button
						color='primary'
						data-testid='optimizeButton'
						onClick={() => {
							console.log("Button got clicked");
							optimizeTour(props);
						}}
					>
						Optimize
					</Button>
				</td>

				<td align={'center'}><LuArrowBigRight fontSize={24}></LuArrowBigRight></td>
				<td align={'center'}><LuArrowBigRightDash fontSize={24}></LuArrowBigRightDash></td>
			</tr>
		</thead>
	);
}

function PlaceList(props) {
	return (
		<tbody>
			{props.places.map((place, index) => (
				<PlaceRow
					{...props}
					key={`table-${JSON.stringify(place)}-${index}`}
					place={place}
					index={index}
				/>
			))}
		</tbody>
	);
}

function PlaceRow(props) {
	const [showFullName, toggleShowFullName] = useToggle(false);
	const name = props.place.defaultDisplayName;
	const location = latLngToText(placeToLatLng(props.place));
	const leg = props.distances.leg[props.index] || 0;
	const cumulative = props.distances.cumulative[props.index] || 0;
	return (
		<tr className={props.selectedIndex === props.index ? 'selected-row' : ''}>
			<td
				data-testid={`place-row-${props.index}`}
				onClick={() =>
					placeRowClicked(
						toggleShowFullName,
						props.placeActions.selectIndex,
						props.index
					)
				}
			>
				<strong>{name}</strong>
				<AdditionalPlaceInfo {...props} showFullName={showFullName} location={location}/>
			</td>
			<td align={'right'}><Distance distance = {leg}/></td>
			<td align={'right'}><Distance distance = {cumulative}/></td>
			<RowArrow toggleShowFullName={toggleShowFullName} index={props.index}/>
		</tr>
	);
}

function AdditionalPlaceInfo(props) {
	return (
		<Collapse isOpen={props.showFullName}>
			{props.place.formatPlace().replace(`${props.place.defaultDisplayName}, `, '')}
			<br />
			{props.location}
			<br />
			<PlaceActions placeActions={props.placeActions} index={props.index} />
		</Collapse>
	);
}

function placeRowClicked(toggleShowFullName, selectIndex, placeIndex) {
	toggleShowFullName();
	selectIndex(placeIndex);
}

function RowArrow(props) {
	return (
		<td>
			<BsChevronDown data-testid={`place-row-toggle-${props.index}`} onClick={props.toggleShowFullName}/>
		</td>
	);
}
