/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PharmacrmTestModule } from '../../../test.module';
import { ProductoDialogComponent } from '../../../../../../main/webapp/app/entities/producto/producto-dialog.component';
import { ProductoService } from '../../../../../../main/webapp/app/entities/producto/producto.service';
import { Producto } from '../../../../../../main/webapp/app/entities/producto/producto.model';
import { PresentacionDeProductoService } from '../../../../../../main/webapp/app/entities/presentacion-de-producto';
import { ProveedorService } from '../../../../../../main/webapp/app/entities/proveedor';

describe('Component Tests', () => {

    describe('Producto Management Dialog Component', () => {
        let comp: ProductoDialogComponent;
        let fixture: ComponentFixture<ProductoDialogComponent>;
        let service: ProductoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [ProductoDialogComponent],
                providers: [
                    PresentacionDeProductoService,
                    ProveedorService,
                    ProductoService
                ]
            })
            .overrideTemplate(ProductoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Producto(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.producto = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'productoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Producto();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.producto = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'productoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
