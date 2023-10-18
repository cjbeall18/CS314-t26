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
import { getOriginalServerUrl, sendAPIRequest } from '../../utils/restfulAPI';
import { element } from 'prop-types';
import { Place } from '../../models/place.model';

export default function AddPlace(props) {
	const [foundPlace, setFoundPlace] = useState();

	const [coordString, setCoordString] = useState('');
	const addPlaceProps = {
		foundPlace,
		setFoundPlace,
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
		verifyCoordinates(props.coordString, props.setFoundPlace);
	}, [props.coordString]);

	return (
		<ModalBody>
			<Col>
				<InputGroup>
					<Input
						onChange={(input) => props.setCoordString(input.target.value)}
						placeholder='Enter Place Coordinates'
						data-testid='coord-input'
						value={props.coordString}
					/>
					<Button data-testid='home-button' onClick={() => props.append(DEFAULT_STARTING_POSITION)}>
						<FaHome/>
					</Button>
				</InputGroup>
				<PlaceInfo foundPlace={props.foundPlace} />
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
					props.append(props.foundPlace);
					props.setCoordString('');
				}}
				data-testid='add-place-button'
				disabled={!props.foundPlace}
			>
				Add Place
			</Button>
		</ModalFooter>
	);
}

async function verifyCoordinates(coordString, setFoundPlace) {
	try {
		if (isCoordinateText(coordString)) {
			const latLngPlace = new Coordinates(coordString);
			const lat = latLngPlace.getLatitude();
			const lng = latLngPlace.getLongitude();
			if (isLatLngValid(lat,lng)) {
				const fullPlace = await reverseGeocode({ lat, lng });
				console.log("fullPlace: ", fullPlace);
				setFoundPlace(fullPlace);
			}
		} else if (coordString.length > 2) {
			console.log("inside else");
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
			console.log("returned response: ", response);
			// let places = [];
			for (let i = 0; i < response.places.length; i++) {
				console.log("entry: ", response.places[i])
				const place = new Place(response.places[i]);
				setFoundPlace(place);
				// places.push(place);
			}
			// response.places.foreach((element) => console.log(element));
		}
	} catch (error) {
		console.log("catch");
		console.log(error);
		setFoundPlace(undefined);
	}
}

function isLatLngValid(lat,lng) {
	return (lat !== undefined && lng !== undefined);
}

function isCoordinateText(inputString) {
    return /^-*[0-9]*.[0-9]*,* *-*[0-9]*.[0-9]*$/.test(inputString);
}