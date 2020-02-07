import { Address } from './address';

export interface Ticket {
    id: number;
    row: number;
    seat: number;
    purchased: boolean;
    datePurchased: string;
    eventDayId: number;
    eventId: number;
    sectorType: string;
    sectorName: string;
    hallName: string;
    locationName: string;
    price: number;
    address: Address;
}
