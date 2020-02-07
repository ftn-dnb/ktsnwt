import { Hall } from './hall';
import { Address } from './address';

export interface Location {
    id: number;
    name: string;
    address: Address;
    halls: Hall[];
}
