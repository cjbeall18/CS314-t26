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
import { Place } from '../../../models/place.model';

export default function Itinerary(props) {

	const [earthRadius, setEarthRadius] = useState(3959.0);
	const [distanceUnits, setDistanceUnits] = useState("miles");
	const [response, setResponse] = useState (1);
	const [places, setPlaces] = useState([]);
	const serverSettings = props.serverSettings;

	useEffect(() => {
		setPlaces(props.places)
    }, [props.places, props.serverSettings]);

	const {distances} = useDistances(places, earthRadius, props.serverSettings);

	const placeListProps = {
		places: places,
		setPlaces: setPlaces,
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
				places = {places}
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
    return requestBody
}

async function optimizeTour (props, setPlaces) {
	const requestBody = createRequestBody(props);
	const responseBody = await sendAPIRequest(requestBody, props.serverSettings.serverUrl);
		let optimizedPlaces = new Array();
		for (let i = 0; i < responseBody.places.length; i++) {
			let place = new Place(responseBody.places[i]);
			optimizedPlaces.push(place);
		}
		props.placeActions.setPlaces(optimizedPlaces);
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
							optimizeTour(props, props.setPlaces);
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
