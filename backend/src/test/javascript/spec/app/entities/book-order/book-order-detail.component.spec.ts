import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BookOrderDetailComponent } from '../../../../../../main/webapp/app/entities/book-order/book-order-detail.component';
import { BookOrderService } from '../../../../../../main/webapp/app/entities/book-order/book-order.service';
import { BookOrder } from '../../../../../../main/webapp/app/entities/book-order/book-order.model';

describe('Component Tests', () => {

    describe('BookOrder Management Detail Component', () => {
        let comp: BookOrderDetailComponent;
        let fixture: ComponentFixture<BookOrderDetailComponent>;
        let service: BookOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [BookOrderDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    BookOrderService
                ]
            }).overrideComponent(BookOrderDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BookOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookOrderService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new BookOrder(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bookOrder).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
