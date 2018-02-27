/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PharmacrmTestModule } from '../../../test.module';
import { DetalleDeRecepcionDeCompraDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/detalle-de-recepcion-de-compra/detalle-de-recepcion-de-compra-delete-dialog.component';
import { DetalleDeRecepcionDeCompraService } from '../../../../../../main/webapp/app/entities/detalle-de-recepcion-de-compra/detalle-de-recepcion-de-compra.service';

describe('Component Tests', () => {

    describe('DetalleDeRecepcionDeCompra Management Delete Component', () => {
        let comp: DetalleDeRecepcionDeCompraDeleteDialogComponent;
        let fixture: ComponentFixture<DetalleDeRecepcionDeCompraDeleteDialogComponent>;
        let service: DetalleDeRecepcionDeCompraService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [DetalleDeRecepcionDeCompraDeleteDialogComponent],
                providers: [
                    DetalleDeRecepcionDeCompraService
                ]
            })
            .overrideTemplate(DetalleDeRecepcionDeCompraDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetalleDeRecepcionDeCompraDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetalleDeRecepcionDeCompraService);
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
