import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookStoreFrontendSharedModule } from '../../shared';

import {
    BookService,
    BookPopupService,
    BookComponent,
    BookDetailComponent,
    BookDialogComponent,
    BookPopupComponent,
    BookDeletePopupComponent,
    BookDeleteDialogComponent,
    bookRoute,
    bookPopupRoute,
} from './';

let ENTITY_STATES = [
    ...bookRoute,
    ...bookPopupRoute,
];

@NgModule({
    imports: [
        BookStoreFrontendSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BookComponent,
        BookDetailComponent,
        BookDialogComponent,
        BookDeleteDialogComponent,
        BookPopupComponent,
        BookDeletePopupComponent,
    ],
    entryComponents: [
        BookComponent,
        BookDialogComponent,
        BookPopupComponent,
        BookDeleteDialogComponent,
        BookDeletePopupComponent,
    ],
    providers: [
        BookService,
        BookPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookStoreFrontendBookModule {}
