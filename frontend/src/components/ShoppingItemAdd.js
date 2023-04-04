import {Button, Input} from "antd";
import {useState} from "react";

export default function ShoppingItemAdd({onSubmit}) {

  const [item, setItem] = useState("");

  return <>
    <Input.Group compact>
      <Input
        value={item}
        onChange={(e) => setItem(e.target.value)}
        style={{width: 'calc(100% - 76px)'}}
        placeholder="Položka seznamu"/>
      <Button onClick={() => {
        onSubmit(item)
        setItem("")
      }} type="primary">Přidat</Button>
    </Input.Group>
  </>


}
