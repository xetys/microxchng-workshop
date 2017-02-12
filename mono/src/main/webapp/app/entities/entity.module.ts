import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MonoBookModule } from './book/book.module';
import { MonoBookOrderModule } from './book-order/book-order.module';
import { MonoCategoryModule } from './category/category.module';
import { MonoCustomerModule } from './customer/customer.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MonoBookModule,
        MonoBookOrderModule,
        MonoCategoryModule,
        MonoCustomerModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MonoEntityModule {}
