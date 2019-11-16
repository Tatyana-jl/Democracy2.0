import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOwnershipType, defaultValue } from 'app/shared/model/ownership-type.model';

export const ACTION_TYPES = {
  FETCH_OWNERSHIPTYPE_LIST: 'ownershipType/FETCH_OWNERSHIPTYPE_LIST',
  FETCH_OWNERSHIPTYPE: 'ownershipType/FETCH_OWNERSHIPTYPE',
  CREATE_OWNERSHIPTYPE: 'ownershipType/CREATE_OWNERSHIPTYPE',
  UPDATE_OWNERSHIPTYPE: 'ownershipType/UPDATE_OWNERSHIPTYPE',
  DELETE_OWNERSHIPTYPE: 'ownershipType/DELETE_OWNERSHIPTYPE',
  RESET: 'ownershipType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOwnershipType>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type OwnershipTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: OwnershipTypeState = initialState, action): OwnershipTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OWNERSHIPTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OWNERSHIPTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_OWNERSHIPTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_OWNERSHIPTYPE):
    case REQUEST(ACTION_TYPES.DELETE_OWNERSHIPTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_OWNERSHIPTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OWNERSHIPTYPE):
    case FAILURE(ACTION_TYPES.CREATE_OWNERSHIPTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_OWNERSHIPTYPE):
    case FAILURE(ACTION_TYPES.DELETE_OWNERSHIPTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_OWNERSHIPTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_OWNERSHIPTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_OWNERSHIPTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_OWNERSHIPTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_OWNERSHIPTYPE):
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

const apiUrl = 'api/ownership-types';

// Actions

export const getEntities: ICrudGetAllAction<IOwnershipType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_OWNERSHIPTYPE_LIST,
    payload: axios.get<IOwnershipType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IOwnershipType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OWNERSHIPTYPE,
    payload: axios.get<IOwnershipType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOwnershipType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OWNERSHIPTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOwnershipType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_OWNERSHIPTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOwnershipType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_OWNERSHIPTYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
