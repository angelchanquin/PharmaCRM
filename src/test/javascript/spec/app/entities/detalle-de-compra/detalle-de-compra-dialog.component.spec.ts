/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PharmacrmTestModule } from '../../../test.module';
import { DetalleDeCompraDialogComponent } from '../../../../../../main/webapp/app/entities/detalle-de-compra/detalle-de-compra-dialog.component';
import { DetalleDeCompraService } from '../../../../../../main/webapp/app/entities/detalle-de-compra/detalle-de-compra.service';
import { DetalleDeCompra } from '../../../../../../main/webapp/app/entities/detalle-de-compra/detalle-de-compra.model';
import { OrdenDeCompraService } from '../../../../../../main/webapp/app/entities/orden-de-compra';
import { ProductoService } from '../../../../../../main/webapp/app/entities/producto';

describe('Component Tests', () => {

    describe('DetalleDeCompra Management Dialog Component', () => {
        let comp: DetalleDeCompraDialogComponent;
        let fixture: ComponentFixture<DetalleDeCompraDialogComponent>;
        let service: DetalleDeCompraService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [DetalleDeCompraDialogComponent],
                providers: [
                    OrdenDeCompraService,
                    ProductoService,
                    DetalleDeCompraService
                ]
            })
            .overrideTemplate(DetalleDeCompraDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetalleDeCompraDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetalleDeCompraService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DetalleDeCompra(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.detalleDeCompra = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'detalleDeCompraListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DetalleDeCompra();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.detalleDeCompra = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'detalleDeCompraListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
