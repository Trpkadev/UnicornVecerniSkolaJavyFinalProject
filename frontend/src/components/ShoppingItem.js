import {Button, Slider} from "antd";
import {useState} from "react";

export default function ShoppingItem({item, onChangeCount, onCompletedClick, onStartAgainClick, onDeleteClick}) {


  const [inputValue, setInputValue] = useState(item.count);

  const onChange = (newValue) => {
    setInputValue(newValue);
    onChangeCount(item.id, newValue)
  };

  return <div style={{ width: "100%" }}>
      {item.content}
    <Slider
      min={1}
      max={20}
      onChange={onChange}
      value={typeof inputValue === 'number' ? inputValue : 0}
    />
    {item.state === "active" && <Button type="dashed" onClick={() => onCompletedClick(item.id)}>Ukončit</Button>}
    {item.state === "completed" && <Button type="dashed" onClick={() => onStartAgainClick(item.id)}>Začít znova</Button>}
    <Button type="dashed" onClick={() => onDeleteClick(item.id)}>Smazat</Button>
  </div>


}
