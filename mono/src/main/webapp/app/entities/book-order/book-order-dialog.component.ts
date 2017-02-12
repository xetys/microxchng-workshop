import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { BookOrder } from './book-order.model';
import { BookOrderPopupService } from './book-order-popup.service';
import { BookOrderService } from './book-order.service';
import { Customer, CustomerService } from '../customer';
import { Book, BookService } from '../book';
@Component({
    selector: 'jhi-book-order-dialog',
    templateUrl: './book-order-dialog.component.html'
})
export class BookOrderDialogComponent implements OnInit {

    bookOrder: BookOrder;
    authorities: any[];
    isSaving: boolean;

    customers: Customer[];

    books: Book[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private bookOrderService: BookOrderService,
        private customerService: CustomerService,
        private bookService: BookService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['bookOrder', 'orderStatus']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.customerService.query().subscribe(
            (res: Response) => { this.customers = res.json(); }, (res: Response) => this.onError(res.json()));
        this.bookService.query().subscribe(
            (res: Response) => { this.books = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.bookOrder.id !== undefined) {
            this.bookOrderService.update(this.bookOrder)
                .subscribe((res: BookOrder) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.bookOrderService.create(this.bookOrder)
                .subscribe((res: BookOrder) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: BookOrder) {
        this.eventManager.broadcast({ name: 'bookOrderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackCustomerById(index: number, item: Customer) {
        return item.id;
    }

    trackBookById(index: number, item: Book) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-book-order-popup',
    template: ''
})
export class BookOrderPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private bookOrderPopupService: BookOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.bookOrderPopupService
                    .open(BookOrderDialogComponent, params['id']);
            } else {
                this.modalRef = this.bookOrderPopupService
                    .open(BookOrderDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
