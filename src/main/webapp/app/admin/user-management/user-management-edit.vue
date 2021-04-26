<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" v-if="userAccount">
        <h2 id="myUserLabel">Criar ou editar um usuário</h2>
        <div>
          <div class="form-group" :hidden="!userAccount.id">
            <label>ID</label>
            <input type="text" class="form-control" name="id" v-model="userAccount.id" readonly />
          </div>

          <div class="form-group">
            <label class="form-control-label">Login</label>
            <input
              type="text"
              class="form-control"
              name="login"
              :class="{ valid: !$v.userAccount.login.$invalid, invalid: $v.userAccount.login.$invalid }"
              v-model="$v.userAccount.login.$model"
            />

            <div v-if="$v.userAccount.login.$anyDirty && $v.userAccount.login.$invalid">
              <small class="form-text text-danger" v-if="!$v.userAccount.login.required"> Este campo é obrigatório. </small>

              <small class="form-text text-danger" v-if="!$v.userAccount.login.maxLength">
                Este campo não pode ultrapassar 50 caracteres.
              </small>

              <small class="form-text text-danger" v-if="!$v.userAccount.login.pattern">
                Esse campo pode conter somente letras, números e endereço de e-mail.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="firstName">Nome</label>
            <input
              type="text"
              class="form-control"
              id="firstName"
              name="firstName"
              :class="{ valid: !$v.userAccount.firstName.$invalid, invalid: $v.userAccount.firstName.$invalid }"
              v-model="$v.userAccount.firstName.$model"
            />
            <div v-if="$v.userAccount.firstName.$anyDirty && $v.userAccount.firstName.$invalid">
              <small class="form-text text-danger" v-if="!$v.userAccount.firstName.maxLength">
                Este campo não pode ultrapassar 50 caracteres.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="lastName">Sobrenome</label>
            <input
              type="text"
              class="form-control"
              id="lastName"
              name="lastName"
              :class="{ valid: !$v.userAccount.lastName.$invalid, invalid: $v.userAccount.lastName.$invalid }"
              v-model="$v.userAccount.lastName.$model"
            />
            <div v-if="$v.userAccount.lastName.$anyDirty && $v.userAccount.lastName.$invalid">
              <small class="form-text text-danger" v-if="!$v.userAccount.lastName.maxLength">
                Este campo não pode ultrapassar 50 caracteres.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="email">E-mail</label>
            <input
              type="email"
              class="form-control"
              id="email"
              name="email"
              :class="{ valid: !$v.userAccount.email.$invalid, invalid: $v.userAccount.email.$invalid }"
              v-model="$v.userAccount.email.$model"
              email
              required
            />
            <div v-if="$v.userAccount.email.$anyDirty && $v.userAccount.email.$invalid">
              <small class="form-text text-danger" v-if="!$v.userAccount.email.required"> Seu e-mail é necessário. </small>
              <small class="form-text text-danger" v-if="!$v.userAccount.email.email"> Seu e-mail é invalido. </small>
              <small class="form-text text-danger" v-if="!$v.userAccount.email.minLength">
                Seu e-mail deve conter ao menos 5 caracteres.
              </small>
              <small class="form-text text-danger" v-if="!$v.userAccount.email.maxLength">
                Seu e-mail não pode ultrapassar 50 caracteres.
              </small>
            </div>
          </div>
          <div class="form-check">
            <label class="form-check-label" for="activated">
              <input
                class="form-check-input"
                :disabled="userAccount.id === null"
                type="checkbox"
                id="activated"
                name="activated"
                v-model="userAccount.activated"
              />
              <span>Activated</span>
            </label>
          </div>

          <div class="form-group">
            <label>Perfis</label>
            <select class="form-control" multiple name="authority" v-model="userAccount.authorities">
              <option v-for="authority of authorities" :value="authority" :key="authority">{{ authority }}</option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancelar</span>
          </button>
          <button type="submit" :disabled="$v.userAccount.$invalid || isSaving" class="btn btn-primary">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Salvar</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts" src="./user-management-edit.component.ts"></script>
