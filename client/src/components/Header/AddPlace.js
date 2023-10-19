import React, { useEffect, useState } from 'react';
import {
	Button,
	Col,
	Modal,
	ModalBody,
	ModalHeader,
	Input,
	InputGroup,
	Collapse,
	ModalFooter,
} from 'reactstrap';
import { FaHome } from 'react-icons/fa';
import Coordinates from 'coordinate-parser';
import { DEFAULT_STARTING_POSITION } from '../../utils/constants';
import { reverseGeocode } from '../../utils/reverseGeocode';
import { Place } from '../../models/place.model';
import { getOriginalServerUrl, sendAPIRequest } from '../../utils/restfulAPI';
import Select from "react-select";

export default function AddPlace(props) {
	const [foundPlaces, setFoundPlaces] = useState([]);
	const [selectedPlace, setSelectedPlace] = useState(null); 

	const [coordString, setCoordString] = useState('');
	const addPlaceProps = {
		foundPlaces,
		setFoundPlaces,
		selectedPlace,
		setSelectedPlace,
		coordString,
		setCoordString,
		append: props.placeActions.append
	}
	return (
		<Modal isOpen={props.showAddPlace} toggle={props.toggleAddPlace}>
			<AddPlaceHeader toggleAddPlace={props.toggleAddPlace} />
			<PlaceSearch {...addPlaceProps}/>
			<AddPlaceFooter
				{...addPlaceProps}
			/>
		</Modal>
	);
}

function AddPlaceHeader(props) {
	return (
		<ModalHeader className='ml-2' toggle={props.toggleAddPlace}>
			Add a Place
		</ModalHeader>
	);
}

function PlaceSearch(props) {
	useEffect(() => {
		verifyCoordinates(props.coordString, props.setFoundPlaces, props.setSelectedPlace);
	}, [props.coordString]);
    
	const placeOptions = props.foundPlaces.map(place => ({
        value: place,
        label: place.formatPlace()
    }));

	return (
		<ModalBody>
			<Col>
				<InputGroup>
					<Input
						onChange={(input) => props.setCoordString(input.target.value)}
						placeholder='Enter Place or Coordinates'
						data-testid='coord-input'
						value={props.coordString}
					/>
					<Button data-testid='home-button' onClick={() => props.append(DEFAULT_STARTING_POSITION)}>
						<FaHome/>
					</Button>
				</InputGroup>
				<Select
					options={placeOptions}
					onChange={(selectedOption) => {
						props.setSelectedPlace(selectedOption ? selectedOption.value : null);
					}}
					placeholder="Select a place..."
					isClearable
					isSearchable
				/>
			</Col>
		</ModalBody>
	);
}

function PlaceInfo(props) {
	return (
		<Collapse isOpen={!!props.foundPlace}>
			<br />
			{props.foundPlace?.formatPlace()}
		</Collapse>
	);
}

function AddPlaceFooter(props) {
	return (
		<ModalFooter>
			<Button
				color='primary'
				onClick={() => {
					props.append(props.selectedPlace);
					props.setCoordString('');
				}}
				data-testid='add-place-button'
				disabled={!props.selectedPlace}
			>
				Add Place
			</Button>
		</ModalFooter>
	);
}

async function verifyCoordinates(coordString, setFoundPlaces, setSelectedPlace) {
	try {
		if (isCoordinateText(coordString)) {
			const latLngPlace = new Coordinates(coordString);
			const lat = latLngPlace.getLatitude();
			const lng = latLngPlace.getLongitude();
			if (isLatLngValid(lat,lng)) {
				const fullPlace = await reverseGeocode({ lat, lng });
				setFoundPlaces([fullPlace]);
				setSelectedPlace(fullPlace);
			}
		} else if (coordString.length > 2) {
			const serverUrl = getOriginalServerUrl();
			let limit = 0;
			if(limit <= 0) {
				limit = 100;
			}
			const requestBody = {
				"requestType": "find",
				"match": coordString,
				"limit": limit
			};

			const response = await sendAPIRequest(requestBody, serverUrl);
			const places = response.places.map(place => new Place(place));
			setFoundPlaces(places);
			setSelectedPlace(null);
		}
	} catch (error) {
		setFoundPlaces([]);
		setSelectedPlace(null);
	}
}

function isLatLngValid(lat,lng) {
	return (lat !== undefined && lng !== undefined);
}

function isCoordinateText(inputString) {
    return /^-*[0-9]*.[0-9]*,* *-*[0-9]*.[0-9]*$/.test(inputString);
}