import React from 'react';
import { beforeEach, describe, expect, jest, test } from '@jest/globals';
import user from '@testing-library/user-event';
import { render, screen, waitFor } from '@testing-library/react';
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
	
	test('evanloy: Limit Input correctly updates the value', async () => {
    	const limitInput = screen.getByTestId('limit-input');  // Assume you have added the 'data-testid' to the limit input
    	user.type(limitInput, '5');

    	await waitFor(() => {
        	expect(limitInput.value).toEqual('5');
    	});
	});

	test('evanloy: Limit Input does not accept non-positive numbers', async () => {
    	const limitInput = screen.getByTestId('limit-input');
    	user.type(limitInput, '-5');

    	await waitFor(() => {
        	expect(limitInput.value).not.toEqual('-5');
    	});
	});

	test('evanloy: Empty Limit Input resets the value', async () => {
    	const limitInput = screen.getByTestId('limit-input');
    	user.clear(limitInput);

    	await waitFor(() => {
        	expect(limitInput.value).toEqual('');
    	});
	});
});
