import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('PresentacionDeProducto e2e test', () => {

    let navBarPage: NavBarPage;
    let presentacionDeProductoDialogPage: PresentacionDeProductoDialogPage;
    let presentacionDeProductoComponentsPage: PresentacionDeProductoComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load PresentacionDeProductos', () => {
        navBarPage.goToEntity('presentacion-de-producto');
        presentacionDeProductoComponentsPage = new PresentacionDeProductoComponentsPage();
        expect(presentacionDeProductoComponentsPage.getTitle())
            .toMatch(/pharmacrmApp.presentacionDeProducto.home.title/);

    });

    it('should load create PresentacionDeProducto dialog', () => {
        presentacionDeProductoComponentsPage.clickOnCreateButton();
        presentacionDeProductoDialogPage = new PresentacionDeProductoDialogPage();
        expect(presentacionDeProductoDialogPage.getModalTitle())
            .toMatch(/pharmacrmApp.presentacionDeProducto.home.createOrEditLabel/);
        presentacionDeProductoDialogPage.close();
    });

    it('should create and save PresentacionDeProductos', () => {
        presentacionDeProductoComponentsPage.clickOnCreateButton();
        presentacionDeProductoDialogPage.setNombreInput('nombre');
        expect(presentacionDeProductoDialogPage.getNombreInput()).toMatch('nombre');
        presentacionDeProductoDialogPage.save();
        expect(presentacionDeProductoDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PresentacionDeProductoComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-presentacion-de-producto div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PresentacionDeProductoDialogPage {
    modalTitle = element(by.css('h4#myPresentacionDeProductoLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nombreInput = element(by.css('input#field_nombre'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNombreInput = function(nombre) {
        this.nombreInput.sendKeys(nombre);
    }

    getNombreInput = function() {
        return this.nombreInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
