/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PharmacrmTestModule } from '../../../test.module';
import { DetalleDeRecepcionDeCompraDialogComponent } from '../../../../../../main/webapp/app/entities/detalle-de-recepcion-de-compra/detalle-de-recepcion-de-compra-dialog.component';
import { DetalleDeRecepcionDeCompraService } from '../../../../../../main/webapp/app/entities/detalle-de-recepcion-de-compra/detalle-de-recepcion-de-compra.service';
import { DetalleDeRecepcionDeCompra } from '../../../../../../main/webapp/app/entities/detalle-de-recepcion-de-compra/detalle-de-recepcion-de-compra.model';
import { ProductoService } from '../../../../../../main/webapp/app/entities/producto';
import { RecepcionDeCompraService } from '../../../../../../main/webapp/app/entities/recepcion-de-compra';

describe('Component Tests', () => {

    describe('DetalleDeRecepcionDeCompra Management Dialog Component', () => {
        let comp: DetalleDeRecepcionDeCompraDialogComponent;
        let fixture: ComponentFixture<DetalleDeRecepcionDeCompraDialogComponent>;
        let service: DetalleDeRecepcionDeCompraService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [DetalleDeRecepcionDeCompraDialogComponent],
                providers: [
                    ProductoService,
                    RecepcionDeCompraService,
                    DetalleDeRecepcionDeCompraService
                ]
            })
            .overrideTemplate(DetalleDeRecepcionDeCompraDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetalleDeRecepcionDeCompraDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetalleDeRecepcionDeCompraService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DetalleDeRecepcionDeCompra(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.detalleDeRecepcionDeCompra = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'detalleDeRecepcionDeCompraListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DetalleDeRecepcionDeCompra();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.detalleDeRecepcionDeCompra = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'detalleDeRecepcionDeCompraListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
