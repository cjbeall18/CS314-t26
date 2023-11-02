import React from 'react';
import { beforeEach, describe, expect, jest, test } from '@jest/globals';
import user from '@testing-library/user-event';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import AddPlace from '../../../src/components/Header/AddPlace';
import { DEFAULT_STARTING_POSITION } from '../../../src/utils/constants';
import {
	REVERSE_GEOCODE_RESPONSE,
	MOCK_PLACE_RESPONSE,
	MOCK_PLACES,
} from '../../sharedMocks';

describe('AddPlace', () => {
	const placeObj = {
		latLng: '40.57, -105.085',
		name: 'Colorado State University, South College Avenue, Fort Collins, Larimer County, Colorado, 80525-1725, United States',
	};

	const props = {
		toggleAddPlace: jest.fn(),
		placeActions: {
			append: jest.fn(),
		},
		showAddPlace: true,
	};

	beforeEach(() => {
		render(
			<AddPlace
				placeActions={props.placeActions}
				showAddPlace={props.showAddPlace}
				toggleAddPlace={props.toggleAddPlace}
			/>
		);
	});

	test('base: validates input', async () => {
		const coordInput = screen.getByTestId('coord-input');
		user.type(coordInput, placeObj.latLng);

		await waitFor(() => {
			expect(coordInput.value).toEqual(placeObj.latLng);
		});
	});

	test('base: adds home', async () => {
		const homeButton = screen.getByTestId('home-button');
		user.click(homeButton);

		await waitFor(() => {
			expect(props.placeActions.append).toHaveBeenCalledWith(DEFAULT_STARTING_POSITION);;
		});
	});

	test('base: handles invalid input', async () => {
		const coordInput = screen.getByTestId('coord-input');
		user.paste(coordInput, '1');

		await waitFor(() => {
			expect(coordInput.value).toEqual('1');
		});

		const addButton = screen.getByTestId('add-place-button');
		expect(addButton.classList.contains('disabled')).toBe(true);
	});

	test('base: Adds place', async () => {
		fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);
		const coordInput = screen.getByTestId('coord-input');
		user.type(coordInput, placeObj.latLng);

		await waitFor(() => {
			expect(coordInput.value).toEqual(placeObj.latLng);
		});

		const addButton = screen.getByTestId('add-place-button');
		expect(addButton.classList.contains('disabled')).toBe(false);
		await waitFor(() => {
			user.click(addButton);
		});
		expect(props.placeActions.append).toHaveBeenCalledWith(MOCK_PLACE_RESPONSE);
		expect(coordInput.value).toEqual('');
	});
	test('cjbeall: test that press of random button sets randomState to true', async () => {
		const randomButton = screen.getByTestId('random-button');
		await waitFor(() => {
			user.click(randomButton);
		});
		const randomState = screen.getByTestId('random-state-element')
		expect(randomState).toBeTruthy();
	});

	// test('clayroby: test that two random places are not equal', async () => {
	// 	// Add the first random location
	// 	const randomButton = screen.getByTestId('random-button');
	// 	await waitFor(() => {
	// 		user.click(randomButton);
	// 	});
	// 	const selectComponent = screen.getByTestId('select-component');
	// 	fireEvent.keyDown(selectComponent.firstChild, {key: 'ArrowDown'});
	// 	fireEvent.keyDown(selectComponent.firstChild, {key: 'ArrowDown'});
	// 	console.log("first child: ", selectComponent.firstChild.nextSibling);
	// 	await waitFor(() => {
	// 		fireEvent.keyDown(selectComponent.firstChild, {key: 'Enter'});
	// 		// fireEvent.mouseDown()
	// 	});
	// 	const addButton = screen.getByTestId('add-place-button');
	// 	expect(addButton.classList.contains('disabled')).toBe(false);
	// 	await waitFor(() => {
	// 		user.click(addButton);
	// 	});
	// 	// Add the second random location
	// 	await waitFor(() => {
	// 		user.click(randomButton);
	// 	});
	// 	expect(addButton.classList.contains('disabled')).toBe(false);
	// 	await waitFor(() => {
	// 		user.click(addButton);
	// 	});
	// 	expect(props.placeActions.length).toEqual(2);
	// });
});
