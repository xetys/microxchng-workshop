const enum OrderStatus {
    'NEW',
    'PAYED',
    'SHIPPED'
};
import { Customer } from '../customer';
import { Book } from '../book';
export class BookOrder {
    constructor(
        public id?: number,
        public status?: OrderStatus,
        public customer?: Customer,
        public book?: Book,
    ) { }
}
