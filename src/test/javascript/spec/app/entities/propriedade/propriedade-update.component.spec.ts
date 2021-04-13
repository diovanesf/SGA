/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import PropriedadeUpdateComponent from '@/entities/propriedade/propriedade-update.vue';
import PropriedadeClass from '@/entities/propriedade/propriedade-update.component';
import PropriedadeService from '@/entities/propriedade/propriedade.service';

import EnderecoService from '@/entities/endereco/endereco.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Propriedade Management Update Component', () => {
    let wrapper: Wrapper<PropriedadeClass>;
    let comp: PropriedadeClass;
    let propriedadeServiceStub: SinonStubbedInstance<PropriedadeService>;

    beforeEach(() => {
      propriedadeServiceStub = sinon.createStubInstance<PropriedadeService>(PropriedadeService);

      wrapper = shallowMount<PropriedadeClass>(PropriedadeUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          propriedadeService: () => propriedadeServiceStub,

          enderecoService: () => new EnderecoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.propriedade = entity;
        propriedadeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(propriedadeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.propriedade = entity;
        propriedadeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(propriedadeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPropriedade = { id: 123 };
        propriedadeServiceStub.find.resolves(foundPropriedade);
        propriedadeServiceStub.retrieve.resolves([foundPropriedade]);

        // WHEN
        comp.beforeRouteEnter({ params: { propriedadeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.propriedade).toBe(foundPropriedade);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
