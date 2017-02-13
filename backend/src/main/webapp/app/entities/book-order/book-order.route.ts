import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BookOrderComponent } from './book-order.component';
import { BookOrderDetailComponent } from './book-order-detail.component';
import { BookOrderPopupComponent } from './book-order-dialog.component';
import { BookOrderDeletePopupComponent } from './book-order-delete-dialog.component';

import { Principal } from '../../shared';


export const bookOrderRoute: Routes = [
  {
    path: 'book-order',
    component: BookOrderComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bookStoreBackendApp.bookOrder.home.title'
    }
  }, {
    path: 'book-order/:id',
    component: BookOrderDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bookStoreBackendApp.bookOrder.home.title'
    }
  }
];

export const bookOrderPopupRoute: Routes = [
  {
    path: 'book-order-new',
    component: BookOrderPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bookStoreBackendApp.bookOrder.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'book-order/:id/edit',
    component: BookOrderPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bookStoreBackendApp.bookOrder.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'book-order/:id/delete',
    component: BookOrderDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bookStoreBackendApp.bookOrder.home.title'
    },
    outlet: 'popup'
  }
];
