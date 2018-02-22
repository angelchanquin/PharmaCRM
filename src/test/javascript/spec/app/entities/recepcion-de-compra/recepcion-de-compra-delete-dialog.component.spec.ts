/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PharmacrmTestModule } from '../../../test.module';
import { RecepcionDeCompraDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/recepcion-de-compra/recepcion-de-compra-delete-dialog.component';
import { RecepcionDeCompraService } from '../../../../../../main/webapp/app/entities/recepcion-de-compra/recepcion-de-compra.service';

describe('Component Tests', () => {

    describe('RecepcionDeCompra Management Delete Component', () => {
        let comp: RecepcionDeCompraDeleteDialogComponent;
        let fixture: ComponentFixture<RecepcionDeCompraDeleteDialogComponent>;
        let service: RecepcionDeCompraService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [RecepcionDeCompraDeleteDialogComponent],
                providers: [
                    RecepcionDeCompraService
                ]
            })
            .overrideTemplate(RecepcionDeCompraDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecepcionDeCompraDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecepcionDeCompraService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
