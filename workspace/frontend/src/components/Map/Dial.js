import {
    IconButton,
    SpeedDial,
    SpeedDialHandler,
    SpeedDialContent,
    SpeedDialAction,
} from "@material-tailwind/react";
import {
    PlusIcon,
    HomeIcon,
    CogIcon,
    Square3Stack3DIcon,
} from "@heroicons/react/24/outline";
import { useMapStore } from '../../store.js';
  
  export function Dial() {
    const { toggleMapType } = useMapStore();

    return (
      <div className="fixed top-6 right-6" style={{ zIndex: 1000}}>
        <SpeedDial placement="bottom">
          <SpeedDialHandler>
            <IconButton size="lg" className="rounded-full">
              <PlusIcon className="h-5 w-5 transition-transform group-hover:rotate-45" />
            </IconButton>
          </SpeedDialHandler>
          <SpeedDialContent className="flex-column">
            <SpeedDialAction>
              <HomeIcon className="h-5 w-5" />
            </SpeedDialAction>
            <SpeedDialAction>
              <CogIcon className="h-5 w-5" />
            </SpeedDialAction>
            <SpeedDialAction>
              <Square3Stack3DIcon className="h-5 w-5" onClick={(e) => {e.stopPropagation(); e.preventDefault(); toggleMapType()}}/>
            </SpeedDialAction>
          </SpeedDialContent>
        </SpeedDial>
      </div>
    );
  }
  