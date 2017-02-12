import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { BookOrder } from './book-order.model';
import { BookOrderPopupService } from './book-order-popup.service';
import { BookOrderService } from './book-order.service';

@Component({
    selector: 'jhi-book-order-delete-dialog',
    templateUrl: './book-order-delete-dialog.component.html'
})
export class BookOrderDeleteDialogComponent {

    bookOrder: BookOrder;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private bookOrderService: BookOrderService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['bookOrder', 'orderStatus']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.bookOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bookOrderListModification',
                content: 'Deleted an bookOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-book-order-delete-popup',
    template: ''
})
export class BookOrderDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private bookOrderPopupService: BookOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.bookOrderPopupService
                .open(BookOrderDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
