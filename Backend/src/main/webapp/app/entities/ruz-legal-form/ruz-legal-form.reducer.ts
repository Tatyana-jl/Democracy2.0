import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRuzLegalForm, defaultValue } from 'app/shared/model/ruz-legal-form.model';

export const ACTION_TYPES = {
  FETCH_RUZLEGALFORM_LIST: 'ruzLegalForm/FETCH_RUZLEGALFORM_LIST',
  FETCH_RUZLEGALFORM: 'ruzLegalForm/FETCH_RUZLEGALFORM',
  CREATE_RUZLEGALFORM: 'ruzLegalForm/CREATE_RUZLEGALFORM',
  UPDATE_RUZLEGALFORM: 'ruzLegalForm/UPDATE_RUZLEGALFORM',
  DELETE_RUZLEGALFORM: 'ruzLegalForm/DELETE_RUZLEGALFORM',
  RESET: 'ruzLegalForm/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRuzLegalForm>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type RuzLegalFormState = Readonly<typeof initialState>;

// Reducer

export default (state: RuzLegalFormState = initialState, action): RuzLegalFormState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RUZLEGALFORM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RUZLEGALFORM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RUZLEGALFORM):
    case REQUEST(ACTION_TYPES.UPDATE_RUZLEGALFORM):
    case REQUEST(ACTION_TYPES.DELETE_RUZLEGALFORM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RUZLEGALFORM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RUZLEGALFORM):
    case FAILURE(ACTION_TYPES.CREATE_RUZLEGALFORM):
    case FAILURE(ACTION_TYPES.UPDATE_RUZLEGALFORM):
    case FAILURE(ACTION_TYPES.DELETE_RUZLEGALFORM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RUZLEGALFORM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_RUZLEGALFORM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RUZLEGALFORM):
    case SUCCESS(ACTION_TYPES.UPDATE_RUZLEGALFORM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RUZLEGALFORM):
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

const apiUrl = 'api/ruz-legal-forms';

// Actions

export const getEntities: ICrudGetAllAction<IRuzLegalForm> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RUZLEGALFORM_LIST,
    payload: axios.get<IRuzLegalForm>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IRuzLegalForm> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RUZLEGALFORM,
    payload: axios.get<IRuzLegalForm>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRuzLegalForm> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RUZLEGALFORM,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRuzLegalForm> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RUZLEGALFORM,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRuzLegalForm> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RUZLEGALFORM,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
