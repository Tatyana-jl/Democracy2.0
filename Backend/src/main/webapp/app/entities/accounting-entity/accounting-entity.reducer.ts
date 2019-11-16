import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAccountingEntity, defaultValue } from 'app/shared/model/accounting-entity.model';

export const ACTION_TYPES = {
  FETCH_ACCOUNTINGENTITY_LIST: 'accountingEntity/FETCH_ACCOUNTINGENTITY_LIST',
  FETCH_ACCOUNTINGENTITY: 'accountingEntity/FETCH_ACCOUNTINGENTITY',
  CREATE_ACCOUNTINGENTITY: 'accountingEntity/CREATE_ACCOUNTINGENTITY',
  UPDATE_ACCOUNTINGENTITY: 'accountingEntity/UPDATE_ACCOUNTINGENTITY',
  DELETE_ACCOUNTINGENTITY: 'accountingEntity/DELETE_ACCOUNTINGENTITY',
  RESET: 'accountingEntity/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAccountingEntity>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type AccountingEntityState = Readonly<typeof initialState>;

// Reducer

export default (state: AccountingEntityState = initialState, action): AccountingEntityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ACCOUNTINGENTITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACCOUNTINGENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ACCOUNTINGENTITY):
    case REQUEST(ACTION_TYPES.UPDATE_ACCOUNTINGENTITY):
    case REQUEST(ACTION_TYPES.DELETE_ACCOUNTINGENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ACCOUNTINGENTITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACCOUNTINGENTITY):
    case FAILURE(ACTION_TYPES.CREATE_ACCOUNTINGENTITY):
    case FAILURE(ACTION_TYPES.UPDATE_ACCOUNTINGENTITY):
    case FAILURE(ACTION_TYPES.DELETE_ACCOUNTINGENTITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACCOUNTINGENTITY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACCOUNTINGENTITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ACCOUNTINGENTITY):
    case SUCCESS(ACTION_TYPES.UPDATE_ACCOUNTINGENTITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ACCOUNTINGENTITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/accounting-entities';

// Actions

export const getEntities: ICrudGetAllAction<IAccountingEntity> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ACCOUNTINGENTITY_LIST,
    payload: axios.get<IAccountingEntity>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IAccountingEntity> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACCOUNTINGENTITY,
    payload: axios.get<IAccountingEntity>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAccountingEntity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ACCOUNTINGENTITY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAccountingEntity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ACCOUNTINGENTITY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAccountingEntity> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ACCOUNTINGENTITY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
