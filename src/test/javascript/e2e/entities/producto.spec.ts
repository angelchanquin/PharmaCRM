import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Producto e2e test', () => {

    let navBarPage: NavBarPage;
    let productoDialogPage: ProductoDialogPage;
    let productoComponentsPage: ProductoComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Productos', () => {
        navBarPage.goToEntity('producto');
        productoComponentsPage = new ProductoComponentsPage();
        expect(productoComponentsPage.getTitle())
            .toMatch(/pharmacrmApp.producto.home.title/);

    });

    it('should load create Producto dialog', () => {
        productoComponentsPage.clickOnCreateButton();
        productoDialogPage = new ProductoDialogPage();
        expect(productoDialogPage.getModalTitle())
            .toMatch(/pharmacrmApp.producto.home.createOrEditLabel/);
        productoDialogPage.close();
    });

   /* it('should create and save Productos', () => {
        productoComponentsPage.clickOnCreateButton();
        productoDialogPage.setSkuInput('5');
        expect(productoDialogPage.getSkuInput()).toMatch('5');
        productoDialogPage.setNombreInput('nombre');
        expect(productoDialogPage.getNombreInput()).toMatch('nombre');
        productoDialogPage.setUpcInput('5');
        expect(productoDialogPage.getUpcInput()).toMatch('5');
        productoDialogPage.setPrecioDeVentaInput('5');
        expect(productoDialogPage.getPrecioDeVentaInput()).toMatch('5');
        productoDialogPage.setPrecioDeVenta2Input('5');
        expect(productoDialogPage.getPrecioDeVenta2Input()).toMatch('5');
        productoDialogPage.setPrecioDeVenta3Input('5');
        expect(productoDialogPage.getPrecioDeVenta3Input()).toMatch('5');
        productoDialogPage.setPrecioDeCompraInput('5');
        expect(productoDialogPage.getPrecioDeCompraInput()).toMatch('5');
        productoDialogPage.setUnidadesEnStockInput('5');
        expect(productoDialogPage.getUnidadesEnStockInput()).toMatch('5');
        productoDialogPage.estadoSelectLastOption();
        productoDialogPage.setFechaDeExpiracionInput('2000-12-31');
        expect(productoDialogPage.getFechaDeExpiracionInput()).toMatch('2000-12-31');
        productoDialogPage.presentacionSelectLastOption();
        productoDialogPage.proveedorSelectLastOption();
        productoDialogPage.save();
        expect(productoDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ProductoComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-producto div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ProductoDialogPage {
    modalTitle = element(by.css('h4#myProductoLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    skuInput = element(by.css('input#field_sku'));
    nombreInput = element(by.css('input#field_nombre'));
    upcInput = element(by.css('input#field_upc'));
    precioDeVentaInput = element(by.css('input#field_precioDeVenta'));
    precioDeVenta2Input = element(by.css('input#field_precioDeVenta2'));
    precioDeVenta3Input = element(by.css('input#field_precioDeVenta3'));
    precioDeCompraInput = element(by.css('input#field_precioDeCompra'));
    unidadesEnStockInput = element(by.css('input#field_unidadesEnStock'));
    estadoSelect = element(by.css('select#field_estado'));
    fechaDeExpiracionInput = element(by.css('input#field_fechaDeExpiracion'));
    presentacionSelect = element(by.css('select#field_presentacion'));
    proveedorSelect = element(by.css('select#field_proveedor'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setSkuInput = function(sku) {
        this.skuInput.sendKeys(sku);
    }

    getSkuInput = function() {
        return this.skuInput.getAttribute('value');
    }

    setNombreInput = function(nombre) {
        this.nombreInput.sendKeys(nombre);
    }

    getNombreInput = function() {
        return this.nombreInput.getAttribute('value');
    }

    setUpcInput = function(upc) {
        this.upcInput.sendKeys(upc);
    }

    getUpcInput = function() {
        return this.upcInput.getAttribute('value');
    }

    setPrecioDeVentaInput = function(precioDeVenta) {
        this.precioDeVentaInput.sendKeys(precioDeVenta);
    }

    getPrecioDeVentaInput = function() {
        return this.precioDeVentaInput.getAttribute('value');
    }

    setPrecioDeVenta2Input = function(precioDeVenta2) {
        this.precioDeVenta2Input.sendKeys(precioDeVenta2);
    }

    getPrecioDeVenta2Input = function() {
        return this.precioDeVenta2Input.getAttribute('value');
    }

    setPrecioDeVenta3Input = function(precioDeVenta3) {
        this.precioDeVenta3Input.sendKeys(precioDeVenta3);
    }

    getPrecioDeVenta3Input = function() {
        return this.precioDeVenta3Input.getAttribute('value');
    }

    setPrecioDeCompraInput = function(precioDeCompra) {
        this.precioDeCompraInput.sendKeys(precioDeCompra);
    }

    getPrecioDeCompraInput = function() {
        return this.precioDeCompraInput.getAttribute('value');
    }

    setUnidadesEnStockInput = function(unidadesEnStock) {
        this.unidadesEnStockInput.sendKeys(unidadesEnStock);
    }

    getUnidadesEnStockInput = function() {
        return this.unidadesEnStockInput.getAttribute('value');
    }

    setEstadoSelect = function(estado) {
        this.estadoSelect.sendKeys(estado);
    }

    getEstadoSelect = function() {
        return this.estadoSelect.element(by.css('option:checked')).getText();
    }

    estadoSelectLastOption = function() {
        this.estadoSelect.all(by.tagName('option')).last().click();
    }
    setFechaDeExpiracionInput = function(fechaDeExpiracion) {
        this.fechaDeExpiracionInput.sendKeys(fechaDeExpiracion);
    }

    getFechaDeExpiracionInput = function() {
        return this.fechaDeExpiracionInput.getAttribute('value');
    }

    presentacionSelectLastOption = function() {
        this.presentacionSelect.all(by.tagName('option')).last().click();
    }

    presentacionSelectOption = function(option) {
        this.presentacionSelect.sendKeys(option);
    }

    getPresentacionSelect = function() {
        return this.presentacionSelect;
    }

    getPresentacionSelectedOption = function() {
        return this.presentacionSelect.element(by.css('option:checked')).getText();
    }

    proveedorSelectLastOption = function() {
        this.proveedorSelect.all(by.tagName('option')).last().click();
    }

    proveedorSelectOption = function(option) {
        this.proveedorSelect.sendKeys(option);
    }

    getProveedorSelect = function() {
        return this.proveedorSelect;
    }

    getProveedorSelectedOption = function() {
        return this.proveedorSelect.element(by.css('option:checked')).getText();
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
