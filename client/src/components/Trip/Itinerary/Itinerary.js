import React, { useState } from 'react';
import { useToggle } from '../../../hooks/useToggle';
import { Table, Collapse } from 'reactstrap';
import { latLngToText, placeToLatLng } from '../../../utils/transformers';
import { BsChevronDown } from 'react-icons/bs';
import PlaceActions from './PlaceActions';
import Distance from './Distance';
import Units from './Units';
import {useDistances} from "../../../hooks/useDistances";

export default function Itinerary(props) {

	const [earthRadius, setEarthRadius] = useState(3959.0);
	const [distanceUnits, setDistanceUnits] = useState("miles");

	const {distances} = useDistances(props.places, earthRadius, props.serverSettings);

	const placeListProps = {
		places: props.places,
		distances: distances,
		placeActions: props.placeActions,
		selectedIndex: props.selectedIndex
	}

	const unitsProps = {
		earthRadius: earthRadius,
		setEarthRadius: setEarthRadius,
		distanceUnits: distanceUnits,
		setDistanceUnits: setDistanceUnits,
	}

	const total = distances.total;
	
	return (
		<Table responsive>
			<TripHeader
				{...unitsProps}
				tripName={props.tripName}
				total = {total}
			/>
			<PlaceList 
				{...placeListProps}
			/>
		</Table>
	);
}

function TripHeader(props) {
	return (
		<thead>
			<tr>
				<th
					className='trip-header-title'
					data-testid='trip-header-title'
				>
					{props.tripName}
				</th>
				<td align={'center'}>Leg Distance</td>
				<td align={'center'}>Cumulative Distance</td>
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
			<td align={'right'}>{/*LOCATION FOR CLAY TO ADD LEGDISTANCE*/}</td>
			<td align={'right'}>{/*LOCATION FOR CLAY TO ADD CUMULATIVEDISTANCE*/}</td>
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
