import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BookStoreFrontendCustomerModule } from './customer/customer.module';
import { BookStoreFrontendBookModule } from './book/book.module';
import { BookStoreFrontendCategoryModule } from './category/category.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BookStoreFrontendCustomerModule,
        BookStoreFrontendBookModule,
        BookStoreFrontendCategoryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookStoreFrontendEntityModule {}
