import { Category } from '../category';
import { BookOrder } from '../book-order';
export class Book {
    constructor(
        public id?: number,
        public title?: string,
        public author?: string,
        public year?: number,
        public price?: number,
        public category?: Category,
        public order?: BookOrder,
    ) { }
}
