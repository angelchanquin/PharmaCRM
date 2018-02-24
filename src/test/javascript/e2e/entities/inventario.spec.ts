import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Inventario e2e test', () => {

    let navBarPage: NavBarPage;
    let inventarioDialogPage: InventarioDialogPage;
    let inventarioComponentsPage: InventarioComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Inventarios', () => {
        navBarPage.goToEntity('inventario');
        inventarioComponentsPage = new InventarioComponentsPage();
        expect(inventarioComponentsPage.getTitle())
            .toMatch(/pharmacrmApp.inventario.home.title/);

    });

    it('should load create Inventario dialog', () => {
        inventarioComponentsPage.clickOnCreateButton();
        inventarioDialogPage = new InventarioDialogPage();
        expect(inventarioDialogPage.getModalTitle())
            .toMatch(/pharmacrmApp.inventario.home.createOrEditLabel/);
        inventarioDialogPage.close();
    });

   /* it('should create and save Inventarios', () => {
        inventarioComponentsPage.clickOnCreateButton();
        inventarioDialogPage.setFechaInput('2000-12-31');
        expect(inventarioDialogPage.getFechaInput()).toMatch('2000-12-31');
        inventarioDialogPage.setCantidadInput('5');
        expect(inventarioDialogPage.getCantidadInput()).toMatch('5');
        inventarioDialogPage.tipoDeMovimientoSelectLastOption();
        inventarioDialogPage.setPrecioInput('5');
        expect(inventarioDialogPage.getPrecioInput()).toMatch('5');
        inventarioDialogPage.setDetallesInput('detalles');
        expect(inventarioDialogPage.getDetallesInput()).toMatch('detalles');
        inventarioDialogPage.productoSelectLastOption();
        inventarioDialogPage.save();
        expect(inventarioDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class InventarioComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-inventario div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class InventarioDialogPage {
    modalTitle = element(by.css('h4#myInventarioLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    fechaInput = element(by.css('input#field_fecha'));
    cantidadInput = element(by.css('input#field_cantidad'));
    tipoDeMovimientoSelect = element(by.css('select#field_tipoDeMovimiento'));
    precioInput = element(by.css('input#field_precio'));
    detallesInput = element(by.css('input#field_detalles'));
    productoSelect = element(by.css('select#field_producto'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setFechaInput = function(fecha) {
        this.fechaInput.sendKeys(fecha);
    }

    getFechaInput = function() {
        return this.fechaInput.getAttribute('value');
    }

    setCantidadInput = function(cantidad) {
        this.cantidadInput.sendKeys(cantidad);
    }

    getCantidadInput = function() {
        return this.cantidadInput.getAttribute('value');
    }

    setTipoDeMovimientoSelect = function(tipoDeMovimiento) {
        this.tipoDeMovimientoSelect.sendKeys(tipoDeMovimiento);
    }

    getTipoDeMovimientoSelect = function() {
        return this.tipoDeMovimientoSelect.element(by.css('option:checked')).getText();
    }

    tipoDeMovimientoSelectLastOption = function() {
        this.tipoDeMovimientoSelect.all(by.tagName('option')).last().click();
    }
    setPrecioInput = function(precio) {
        this.precioInput.sendKeys(precio);
    }

    getPrecioInput = function() {
        return this.precioInput.getAttribute('value');
    }

    setDetallesInput = function(detalles) {
        this.detallesInput.sendKeys(detalles);
    }

    getDetallesInput = function() {
        return this.detallesInput.getAttribute('value');
    }

    productoSelectLastOption = function() {
        this.productoSelect.all(by.tagName('option')).last().click();
    }

    productoSelectOption = function(option) {
        this.productoSelect.sendKeys(option);
    }

    getProductoSelect = function() {
        return this.productoSelect;
    }

    getProductoSelectedOption = function() {
        return this.productoSelect.element(by.css('option:checked')).getText();
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
