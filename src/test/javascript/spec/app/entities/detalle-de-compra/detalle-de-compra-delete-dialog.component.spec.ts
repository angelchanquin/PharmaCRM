/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PharmacrmTestModule } from '../../../test.module';
import { DetalleDeCompraDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/detalle-de-compra/detalle-de-compra-delete-dialog.component';
import { DetalleDeCompraService } from '../../../../../../main/webapp/app/entities/detalle-de-compra/detalle-de-compra.service';

describe('Component Tests', () => {

    describe('DetalleDeCompra Management Delete Component', () => {
        let comp: DetalleDeCompraDeleteDialogComponent;
        let fixture: ComponentFixture<DetalleDeCompraDeleteDialogComponent>;
        let service: DetalleDeCompraService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [DetalleDeCompraDeleteDialogComponent],
                providers: [
                    DetalleDeCompraService
                ]
            })
            .overrideTemplate(DetalleDeCompraDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetalleDeCompraDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetalleDeCompraService);
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
