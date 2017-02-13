import { User } from '../../shared';
import { BookOrder } from '../book-order';
export class Customer {
    constructor(
        public id?: number,
        public streetName?: string,
        public streetNumber?: string,
        public postalCode?: string,
        public city?: string,
        public user?: User,
        public order?: BookOrder,
    ) { }
}
