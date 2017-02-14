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
import { BookDetailComponent } from '../../../../../../main/webapp/app/entities/book/book-detail.component';
import { BookService } from '../../../../../../main/webapp/app/entities/book/book.service';
import { Book } from '../../../../../../main/webapp/app/entities/book/book.model';
import {AccountService} from "../../../../../../main/webapp/app/shared/auth/account.service";
import { AlertService } from  'ng-jhipster';

describe('Component Tests', () => {

    describe('Book Management Detail Component', () => {
        let comp: BookDetailComponent;
        let fixture: ComponentFixture<BookDetailComponent>;
        let service: BookService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [BookDetailComponent],
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
                    BookService,
                    AccountService,
                    AlertService
                ]
            }).overrideComponent(BookDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BookDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookService);
        });


    });

});
