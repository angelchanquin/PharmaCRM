import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('RecepcionDeCompra e2e test', () => {

    let navBarPage: NavBarPage;
    let recepcionDeCompraDialogPage: RecepcionDeCompraDialogPage;
    let recepcionDeCompraComponentsPage: RecepcionDeCompraComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load RecepcionDeCompras', () => {
        navBarPage.goToEntity('recepcion-de-compra');
        recepcionDeCompraComponentsPage = new RecepcionDeCompraComponentsPage();
        expect(recepcionDeCompraComponentsPage.getTitle())
            .toMatch(/pharmacrmApp.recepcionDeCompra.home.title/);

    });

    it('should load create RecepcionDeCompra dialog', () => {
        recepcionDeCompraComponentsPage.clickOnCreateButton();
        recepcionDeCompraDialogPage = new RecepcionDeCompraDialogPage();
        expect(recepcionDeCompraDialogPage.getModalTitle())
            .toMatch(/pharmacrmApp.recepcionDeCompra.home.createOrEditLabel/);
        recepcionDeCompraDialogPage.close();
    });

   /* it('should create and save RecepcionDeCompras', () => {
        recepcionDeCompraComponentsPage.clickOnCreateButton();
        recepcionDeCompraDialogPage.setNoDeReciboInput('noDeRecibo');
        expect(recepcionDeCompraDialogPage.getNoDeReciboInput()).toMatch('noDeRecibo');
        recepcionDeCompraDialogPage.setFechaDeRecepcionInput('2000-12-31');
        expect(recepcionDeCompraDialogPage.getFechaDeRecepcionInput()).toMatch('2000-12-31');
        recepcionDeCompraDialogPage.tipoSelectLastOption();
        recepcionDeCompraDialogPage.setNotasInput('notas');
        expect(recepcionDeCompraDialogPage.getNotasInput()).toMatch('notas');
        recepcionDeCompraDialogPage.ordenDeCompraSelectLastOption();
        recepcionDeCompraDialogPage.save();
        expect(recepcionDeCompraDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class RecepcionDeCompraComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-recepcion-de-compra div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class RecepcionDeCompraDialogPage {
    modalTitle = element(by.css('h4#myRecepcionDeCompraLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    noDeReciboInput = element(by.css('input#field_noDeRecibo'));
    fechaDeRecepcionInput = element(by.css('input#field_fechaDeRecepcion'));
    tipoSelect = element(by.css('select#field_tipo'));
    notasInput = element(by.css('input#field_notas'));
    ordenDeCompraSelect = element(by.css('select#field_ordenDeCompra'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNoDeReciboInput = function(noDeRecibo) {
        this.noDeReciboInput.sendKeys(noDeRecibo);
    }

    getNoDeReciboInput = function() {
        return this.noDeReciboInput.getAttribute('value');
    }

    setFechaDeRecepcionInput = function(fechaDeRecepcion) {
        this.fechaDeRecepcionInput.sendKeys(fechaDeRecepcion);
    }

    getFechaDeRecepcionInput = function() {
        return this.fechaDeRecepcionInput.getAttribute('value');
    }

    setTipoSelect = function(tipo) {
        this.tipoSelect.sendKeys(tipo);
    }

    getTipoSelect = function() {
        return this.tipoSelect.element(by.css('option:checked')).getText();
    }

    tipoSelectLastOption = function() {
        this.tipoSelect.all(by.tagName('option')).last().click();
    }
    setNotasInput = function(notas) {
        this.notasInput.sendKeys(notas);
    }

    getNotasInput = function() {
        return this.notasInput.getAttribute('value');
    }

    ordenDeCompraSelectLastOption = function() {
        this.ordenDeCompraSelect.all(by.tagName('option')).last().click();
    }

    ordenDeCompraSelectOption = function(option) {
        this.ordenDeCompraSelect.sendKeys(option);
    }

    getOrdenDeCompraSelect = function() {
        return this.ordenDeCompraSelect;
    }

    getOrdenDeCompraSelectedOption = function() {
        return this.ordenDeCompraSelect.element(by.css('option:checked')).getText();
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
