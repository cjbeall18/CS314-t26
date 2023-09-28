import Units from '../../../src/components/Trip/Itinerary/Units';

describe('Units', () => {
    
    test('evanloy: It should always return "miles"', () => {
        const result = Units();
        expect(result).toBe("miles");
    });

});