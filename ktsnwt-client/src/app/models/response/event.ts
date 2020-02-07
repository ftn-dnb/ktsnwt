import { Hall } from './hall';
import { EventDay } from './eventDay';
import { Pricing } from './pricing';

export interface Event {
    id: number;
    name: string;
    startDate: string;
    endDate: string;
    purchaseLimit: number;
    ticketsPerUser: number;
    description: string;
    imagePath: string;
    type: string;
    hall: Hall;
    days: EventDay[];
    pricings: Pricing[];
    location: Location;
}
