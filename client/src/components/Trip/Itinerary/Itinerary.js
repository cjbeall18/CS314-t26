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
	const placeListProps = {
		places: props.places,
		placeActions: props.placeActions,
		selectedIndex: props.selectedIndex
	}
	
	return (
		<Table responsive>
			<TripHeader
				tripName={props.tripName}
				legTitle={props.legTitle}
				cumulativeTitle={props.cumulativeTitle}
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
				<th
					className='leg-header-title'
					data-testid='leg-header-title'
				>
					{props.legTitle}
				</th>
				<th
					className='cumulative-header-title'
					data-testid='cumulative-header-title'
				>
					{props.cumulativeTitle}
					</th>
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
