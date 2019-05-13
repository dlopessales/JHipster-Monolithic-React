import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDetalhe, defaultValue } from 'app/shared/model/detalhe.model';

export const ACTION_TYPES = {
  FETCH_DETALHE_LIST: 'detalhe/FETCH_DETALHE_LIST',
  FETCH_DETALHE: 'detalhe/FETCH_DETALHE',
  CREATE_DETALHE: 'detalhe/CREATE_DETALHE',
  UPDATE_DETALHE: 'detalhe/UPDATE_DETALHE',
  DELETE_DETALHE: 'detalhe/DELETE_DETALHE',
  RESET: 'detalhe/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDetalhe>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DetalheState = Readonly<typeof initialState>;

// Reducer

export default (state: DetalheState = initialState, action): DetalheState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DETALHE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DETALHE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DETALHE):
    case REQUEST(ACTION_TYPES.UPDATE_DETALHE):
    case REQUEST(ACTION_TYPES.DELETE_DETALHE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DETALHE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DETALHE):
    case FAILURE(ACTION_TYPES.CREATE_DETALHE):
    case FAILURE(ACTION_TYPES.UPDATE_DETALHE):
    case FAILURE(ACTION_TYPES.DELETE_DETALHE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DETALHE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DETALHE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DETALHE):
    case SUCCESS(ACTION_TYPES.UPDATE_DETALHE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DETALHE):
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

const apiUrl = 'api/detalhes';

// Actions

export const getEntities: ICrudGetAllAction<IDetalhe> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DETALHE_LIST,
    payload: axios.get<IDetalhe>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDetalhe> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DETALHE,
    payload: axios.get<IDetalhe>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDetalhe> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DETALHE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDetalhe> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DETALHE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDetalhe> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DETALHE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
