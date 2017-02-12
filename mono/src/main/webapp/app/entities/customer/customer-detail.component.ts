import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Customer } from './customer.model';
import { CustomerService } from './customer.service';

@Component({
    selector: 'jhi-customer-detail',
    templateUrl: './customer-detail.component.html'
})
export class CustomerDetailComponent implements OnInit, OnDestroy {

    customer: Customer;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private customerService: CustomerService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['customer']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.customerService.find(id).subscribe(customer => {
            this.customer = customer;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
