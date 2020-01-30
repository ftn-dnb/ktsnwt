import { Sector } from './sector';

export interface Hall {
    id: number,
    name: string,
    locationId: number,
    sectors: Sector[]
}