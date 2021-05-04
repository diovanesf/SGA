<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="amostra">
        <h2 class="jh-entity-heading" data-cy="amostraDetailsHeading"><span>Amostra</span> {{ amostra.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Protocolo</span>
          </dt>
          <dd>
            <span>{{ amostra.protocolo }}</span>
          </dd>
          <dt>
            <span>Forma de envio</span>
          </dt>
          <dd>
            <span>{{ amostra.formaEnvio }}</span>
          </dd>
          <dt>
            <span>Número de amostras</span>
          </dt>
          <dd>
            <span>{{ amostra.numeroAmostras }}</span>
          </dd>
          <dt>
            <span>Espécie</span>
          </dt>
          <dd>
            <span>{{ amostra.especie }}</span>
          </dd>
          <dt>
            <span>Numero Animais</span>
          </dt>
          <dd>
            <span>{{ amostra.numeroAnimais }}</span>
          </dd>
          <dt>
            <span>Acometidos</span>
          </dt>
          <dd>
            <span>{{ amostra.acometidos }}</span>
          </dd>
          <dt>
            <span>Pricipal Suspeita</span>
          </dt>
          <dd>
            <span>{{ amostra.pricipalSuspeita }}</span>
          </dd>
          <dt>
            <span>Data Inicial</span>
          </dt>
          <dd>
            <span>{{ amostra.dataInicial }}</span>
          </dd>
          <dt>
            <span>Material Recebido</span>
          </dt>
          <dd>
            <span>{{ amostra.materialRecebido }}</span>
          </dd>
          <dt>
            <span>Acondicionamento</span>
          </dt>
          <dd>
            <span>{{ amostra.acondicionamento }}</span>
          </dd>
          <dt>
            <span>Condição do Material</span>
          </dt>
          <dd>
            <span>{{ amostra.condicaoMaterial }}</span>
          </dd>
          <dt>
            <span>Status</span>
          </dt>
          <dd>
            <span>{{ amostra.status }}</span>
          </dd>
          <dt>
            <span>Tipo Med. Vet.</span>
          </dt>
          <dd>
            <span>{{ amostra.tipoMedVet }}</span>
          </dd>
          <dt>
            <span>Valor Total</span>
          </dt>
          <dd>
            <span>{{ amostra.valorTotal }}</span>
          </dd>
          <dt>
            <span>Tipo de Pagamento</span>
          </dt>
          <dd>
            <span>{{ amostra.tipoPagamento }}</span>
          </dd>
          <dt>
            <span>Tipo</span>
          </dt>
          <dd>
            <span>{{ amostra.tipo }}</span>
          </dd>
          <dt>
            <span>Situacao</span>
          </dt>
          <dd>
            <span>{{ amostra.situacao }}</span>
          </dd>
          <dt>
            <span>User</span>
          </dt>
          <dd>
            <span v-for="(user, i) in amostra.users" :key="user.id"
              >{{ i > 0 ? ', ' : '' }}
              {{ user.login }}
            </span>
          </dd>
          <dt>
            <span>Propriedade</span>
          </dt>
          <dd>
            <div v-if="amostra.propriedade">
              <router-link :to="{ name: 'PropriedadeView', params: { propriedadeId: amostra.propriedade.id } }">{{
                amostra.propriedade.endereco.endereco
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Médico Veterinário</span>
          </dt>
          <dd>
            <div v-if="amostra.medicoveterinario">
              <router-link :to="{ name: 'MedicoveterinarioView', params: { medicoveterinarioId: amostra.medicoveterinario.id } }">{{
                amostra.medicoveterinario.nome
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Vacina</span>
          </dt>
          <dd>
            <div v-if="amostra.vacina">
              <router-link :to="{ name: 'VacinaView', params: { vacinaId: amostra.vacina.id } }">{{ amostra.vacina.status }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-outline-success" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Voltar</span>
        </button>
        <router-link v-if="amostra.id" :to="{ name: 'AmostraEdit', params: { amostraId: amostra.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-success">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Editar</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./amostra-details.component.ts"></script>
