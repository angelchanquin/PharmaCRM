/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PharmacrmTestModule } from '../../../test.module';
import { PresentacionDeProductoDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/presentacion-de-producto/presentacion-de-producto-delete-dialog.component';
import { PresentacionDeProductoService } from '../../../../../../main/webapp/app/entities/presentacion-de-producto/presentacion-de-producto.service';

describe('Component Tests', () => {

    describe('PresentacionDeProducto Management Delete Component', () => {
        let comp: PresentacionDeProductoDeleteDialogComponent;
        let fixture: ComponentFixture<PresentacionDeProductoDeleteDialogComponent>;
        let service: PresentacionDeProductoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PharmacrmTestModule],
                declarations: [PresentacionDeProductoDeleteDialogComponent],
                providers: [
                    PresentacionDeProductoService
                ]
            })
            .overrideTemplate(PresentacionDeProductoDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PresentacionDeProductoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PresentacionDeProductoService);
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
