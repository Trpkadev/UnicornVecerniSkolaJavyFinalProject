import ShoppingItemAdd from "./ShoppingItemAdd";
import {useEffect, useState} from "react";
import Calls from "../Calls";
import {Button, List, message, Spin} from "antd";
import ShoppingItem from "./ShoppingItem";

export default function ShoppingList() {

  const [items, setItems] = useState([])
  const [isLoading, setIsLoading] = useState(false)
  const [messageApi, contextHolder] = message.useMessage();

  const errorMessage = (e) => {
    messageApi.open({
      type: 'error',
      content: `Chyba při komunikaci se serverem: ${e.message}`,
    });
  };

  const successMessage = (message) => {
    messageApi.open({
      type: 'success',
      content: message,
    });
  };


  useEffect(() => {
    loadOnlyActive();
  }, [])

  const loadOnlyActive = async () => {
    setIsLoading(true)
    try {
      const response = await Calls.getShoppingListOnlyActive();
      setItems(response)
    } catch (e) {
      errorMessage(e)
    } finally {
      setIsLoading(false)
    }
  }

  const loadOnlyCompleted = async () => {
    setIsLoading(true)
    try {
      const response = await Calls.getShoppingListOnlyCompleted();
      setItems(response)
    } catch (e) {
      errorMessage(e)
    } finally {
      setIsLoading(false)
    }

  }

  const loadAll = async () => {
    setIsLoading(true)
    try {
      const response = await Calls.getShoppingListAll();
      setItems(response)
    } catch (e) {
      errorMessage(e)
    } finally {
      setIsLoading(false)
    }

  }

  const createItem = async (newItem) => {
    await withLoaderAndReload(async () => {
      await Calls.createShoppingItem({content: newItem, state: 'active', count: 1})
      successMessage(`Nová položka vytvořena`)
    })

  }

  const onChangeCount = async (id, count) => {
    await withLoaderAndReload(async () => {
      await Calls.updateShoppingItem(id, {count: count})
      successMessage(`Změněm počet u položky`)
    })
  }

  const onCompletedClick = async (id) => {
    await withLoaderAndReload(async () => {
      await Calls.updateShoppingItem(id, {state: "completed"})
      successMessage(`Položka byla označena za dokončenou`)
    })
  }

  const onDeleteClick = async (id) => {
    await withLoaderAndReload(async () => {
      await Calls.deleteShoppingItem(id)
      successMessage(`Položka byla smazána`)
    })
  }

  const onStartAgainClick = async (id) => {
    await withLoaderAndReload(async () => {
      await Calls.updateShoppingItem(id, {state: "active"})
      successMessage(`Položka znova zařazena`)
    })
  }

  const withLoaderAndReload = async (func) => {
    setIsLoading(true)
    try {
      await func()
      await loadOnlyActive()
    } catch (e) {
      errorMessage(e)
    } finally {
      setIsLoading(false)
    }
  }


  const filterButtons = <>
    <Button type="text">Filtry:</Button>
    <Button type="link" onClick={loadAll}>Vše</Button>
    <Button type="link" onClick={loadOnlyActive}>Aktivní</Button>
    <Button type="link" onClick={loadOnlyCompleted}>Ukončené</Button>
  </>


  return <>
    {contextHolder}
    <Spin tip="Načítám data..." spinning={isLoading}>
      <ShoppingItemAdd onSubmit={createItem}/>
      <List
        size="large"
        header={<h2>Položky seznamu</h2>}
        footer={filterButtons}
        bordered
        dataSource={items}
        renderItem={(item) => <List.Item><ShoppingItem item={item} onChangeCount={onChangeCount} onCompletedClick={onCompletedClick} onStartAgainClick={onStartAgainClick} onDeleteClick={onDeleteClick}/></List.Item>}
      />
    </Spin>

  </>
}
