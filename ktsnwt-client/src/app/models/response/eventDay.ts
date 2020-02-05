import { Pricing } from './pricing';

export interface EventDay {
    id: number;
    name: string;
    description: string;
    date: Date;
    status: string;
    eventId: number;
    pricing: Pricing[];
}
