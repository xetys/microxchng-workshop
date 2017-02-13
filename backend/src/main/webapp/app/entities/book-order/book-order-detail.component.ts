import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { BookOrder } from './book-order.model';
import { BookOrderService } from './book-order.service';

@Component({
    selector: 'jhi-book-order-detail',
    templateUrl: './book-order-detail.component.html'
})
export class BookOrderDetailComponent implements OnInit, OnDestroy {

    bookOrder: BookOrder;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private bookOrderService: BookOrderService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['bookOrder', 'orderStatus']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.bookOrderService.find(id).subscribe(bookOrder => {
            this.bookOrder = bookOrder;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
