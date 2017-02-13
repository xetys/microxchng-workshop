import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BookStoreBackendBookModule } from './book/book.module';
import { BookStoreBackendBookOrderModule } from './book-order/book-order.module';
import { BookStoreBackendCategoryModule } from './category/category.module';
import { BookStoreBackendCustomerModule } from './customer/customer.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BookStoreBackendBookModule,
        BookStoreBackendBookOrderModule,
        BookStoreBackendCategoryModule,
        BookStoreBackendCustomerModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookStoreBackendEntityModule {}
