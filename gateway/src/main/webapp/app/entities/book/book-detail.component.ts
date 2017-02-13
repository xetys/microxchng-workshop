import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Book } from './book.model';
import { BookService } from './book.service';
import {AccountService} from '../../shared/auth/account.service';
import { AlertService } from  'ng-jhipster';
import {Customer} from "../customer/customer.model";

@Component({
    selector: 'jhi-book-detail',
    templateUrl: './book-detail.component.html'
})
export class BookDetailComponent implements OnInit, OnDestroy {

    book: Book;
    private subscription: any;
    private customer: Customer;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private bookService: BookService,
        private route: ActivatedRoute,
        private accountService: AccountService,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['book']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });

        this.accountService.getCustomer().subscribe(customer => this.customer = customer);
    }

    orderBook(book: Book) {
        this.bookService.orderBook(book, this.customer)
            .subscribe(response => {
                this.alertService.success('bookServiceApp.book.ordered');

            });
    }

    load (id) {
        this.bookService.find(id).subscribe(book => {
            this.book = book;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
