import Distance from '../../../src/components/Trip/Itinerary/Distance';

describe('Distance', () => {
    test('evanloy: It should format the distance correctly for default locale (US)', () => {
        const mockProps = { distance: 1234.5678 };
        const result = Distance(mockProps);
        expect(result).toBe("1,234.568"); 
    });

    test('evanloy: It should handle integers correctly', () => {
        const mockProps = { distance: 1000 };
        const result = Distance(mockProps);
        expect(result).toBe("1,000"); 
    });

    test('evanloy: It should handle small numbers correctly', () => {
        const mockProps = { distance: 50 };
        const result = Distance(mockProps);
        expect(result).toBe("50"); 
    });

    test('evanloy: It should return "0" for a distance of zero', () => {
        const mockProps = { distance: 0 };
        const result = Distance(mockProps);
        expect(result).toBe("0");
    });
});
