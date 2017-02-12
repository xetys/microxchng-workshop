import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MonoSharedModule } from '../../shared';

import {
    BookOrderService,
    BookOrderPopupService,
    BookOrderComponent,
    BookOrderDetailComponent,
    BookOrderDialogComponent,
    BookOrderPopupComponent,
    BookOrderDeletePopupComponent,
    BookOrderDeleteDialogComponent,
    bookOrderRoute,
    bookOrderPopupRoute,
} from './';

let ENTITY_STATES = [
    ...bookOrderRoute,
    ...bookOrderPopupRoute,
];

@NgModule({
    imports: [
        MonoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BookOrderComponent,
        BookOrderDetailComponent,
        BookOrderDialogComponent,
        BookOrderDeleteDialogComponent,
        BookOrderPopupComponent,
        BookOrderDeletePopupComponent,
    ],
    entryComponents: [
        BookOrderComponent,
        BookOrderDialogComponent,
        BookOrderPopupComponent,
        BookOrderDeleteDialogComponent,
        BookOrderDeletePopupComponent,
    ],
    providers: [
        BookOrderService,
        BookOrderPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MonoBookOrderModule {}
