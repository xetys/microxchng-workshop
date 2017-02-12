import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BookComponent } from './book.component';
import { BookDetailComponent } from './book-detail.component';
import { BookPopupComponent } from './book-dialog.component';
import { BookDeletePopupComponent } from './book-delete-dialog.component';

import { Principal } from '../../shared';


export const bookRoute: Routes = [
  {
    path: 'book',
    component: BookComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bookStoreFrontendApp.book.home.title'
    }
  }, {
    path: 'book/:id',
    component: BookDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bookStoreFrontendApp.book.home.title'
    }
  }
];

export const bookPopupRoute: Routes = [
  {
    path: 'book-new',
    component: BookPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bookStoreFrontendApp.book.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'book/:id/edit',
    component: BookPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bookStoreFrontendApp.book.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'book/:id/delete',
    component: BookDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bookStoreFrontendApp.book.home.title'
    },
    outlet: 'popup'
  }
];
